package br.com.biosecure.model;

import br.com.biosecure.builders.*;
import br.com.biosecure.model.CultureMedia.Presentation;
import br.com.biosecure.model.CultureMedia.QuantificationUnit;
import br.com.biosecure.model.CultureMedia.StorageConditions;
import br.com.biosecure.model.FaceProtection.ProtectionType;
import br.com.biosecure.model.Glove.GloveMaterial;
import br.com.biosecure.model.LabCoat.FabricType;
import br.com.biosecure.model.PPE.Size;
import br.com.biosecure.model.Product.MeasureUnit;
import br.com.biosecure.model.Product.PackagingType;
import br.com.biosecure.model.SampleBag.FilterType;
import br.com.biosecure.model.SampleContainer.Material;
import br.com.biosecure.model.SampleContainer.SterilizationMethod;
import br.com.biosecure.model.Sanitizer.PhysicalForm;
import br.com.biosecure.model.TestTube.BottomType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import java.util.List;
import java.util.OptionalDouble;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(Lifecycle.PER_CLASS)
class SkuTest {

    @Test
    @BeforeAll
    void shouldThrowException_WhenProductIsInvalid() {
        SkuGenerationException skuException = assertThrows(SkuGenerationException.class, () -> {
            DummyProduct.builder().build().getSku();
        });

        NullPointerException skuExceptionForNull = assertThrows(NullPointerException.class, () -> {
            new SKU(null);
        });

        assertEquals("Unknow product type. Generation of SKU code is unavailable for this subclass.\n Unknow type provided: DummyProduct", skuException.getMessage());

        assertEquals("The product mustn't be null.", skuExceptionForNull.getMessage());
    }
    
    @Test
    void shouldGenerateSkuCorrectly_WhenProductIsASanitizer() {
        Sanitizer sanitizerAmmonium = SanitizerTestBuilder.aSanitizer()
            .withQuantityPerPackage(5)
            .withMeasureUnit(MeasureUnit.L)
            .withPackagingType(PackagingType.GALLON)
            .withActiveIngredient(List.of(
                    new Ingredient("Alkyl Dimethyl Benzyl Ammonium Chloride", "68424-85-1", Ingredient.ChemicalFamily.QUATERNARY_AMMONIUM, 50, Ingredient.IngredientType.ACTIVE_INGREDIENT),
                    new Ingredient("Didecyl Dimethyl Ammonium Chloride", "7173-51-5", Ingredient.ChemicalFamily.QUATERNARY_AMMONIUM, 50, Ingredient.IngredientType.ACTIVE_INGREDIENT)
            ))
            .withFlammable(false)
            .withForm(PhysicalForm.LIQUID)
            .build();
        
        assertEquals("SAN-G5L-QA-LQNF", sanitizerAmmonium.getSku().getSkuCode());

        Sanitizer sanitizerAlcohol = SanitizerTestBuilder.aSanitizer()
            .withQuantityPerPackage(1)
            .withMeasureUnit(MeasureUnit.L)
            .withPackagingType(PackagingType.BOTTLE)
            .withActiveIngredient(List.of(IngredientTestBuilder.anActiveIngredient().build())) // Alcohol isopropyl is the default build of IngredientBuilder
            .withFlammable(true)
            .withForm(PhysicalForm.LIQUID)
            .withMainChemicalFamily(Ingredient.ChemicalFamily.ALCOHOL)
            .build();

        assertEquals("SAN-BT1L-AL-LQFL", sanitizerAlcohol.getSku().getSkuCode());
    }
    
    @Test
    void shouldGenerateSkuCorrectly_WhenProductIsACultureMedia() {
        CultureMedia cultureMedia = CultureMediaTestBuilder.aCultureMediaBuilder()
            .withPackagingType(PackagingType.BOX)
            .withQuantityPerPackage(50)
            .withQuantificationUnit(QuantificationUnit.ML)
            .withQuantityPerUnit(7)
            .withPreparationGramsPerLiter(null)
            .withPresentationForm(Presentation.PREPARED_LIQUID_TUBE)
            .withProtectOfLight(true)
            .withStorageConditions(StorageConditions.REFRIGERATED)
            .build();
        
        assertEquals("CTM-BX50-7ML-FREF", cultureMedia.getSku().getSkuCode());

        CultureMedia cultureMedia2 = CultureMediaTestBuilder.aCultureMediaBuilder()
            .withQuantificationUnit(QuantificationUnit.MG)
            .withQuantityPerUnit(5)
            .withPreparationGramsPerLiter(OptionalDouble.of(2.5))
            .withPresentationForm(Presentation.DEHYDRATED_POWDER_SACHET)
            .withProtectOfLight(false)
            .withStorageConditions(StorageConditions.FRESH)
            .build();

        assertEquals("CTM-P20-5MG-NFRE", cultureMedia2.getSku().getSkuCode());
    }
    
    @Nested
    @TestInstance(Lifecycle.PER_CLASS)
    class WhenSubclassOfPpe  {
        
        @Test
        void shouldGenerateSkuCorrectly_WhenProductIsAGlove() {
            Glove glove = GloveTestBuilder.aGlove()
                .withMaterial(GloveMaterial.NITRILE)
                .withLongBarrel(false)
                .withThicknessMils(6)
                .build();
                
            assertEquals("GLV-P20-6NT-SU", glove.getSku().getSkuCode());

            Glove glove2 = GloveTestBuilder.aGlove()
                .withMaterial(GloveMaterial.LATEX)
                .withThicknessMils(4)
                .withLongBarrel(true)
                .build();

            assertEquals("GLV-P20-4LA-LU", glove2.getSku().getSkuCode());
        }

        @Test
        void shouldGenerateSkuCorrectly_WhenProductIsAFaceProtection() {
            FaceProtection goggles = FaceProtectionTestBuilder.aFaceProtection()
                .withAntiFog(false)
                .withType(ProtectionType.GOGGLES)
                .withSize(Size.MEDIUM)
                .build();
                
            assertEquals("FPT-P20-GG-CM", goggles.getSku().getSkuCode());

            FaceProtection maskRespirator = FaceProtectionTestBuilder.aFaceProtection()
                .withAntiFog(true)
                .withSize(Size.LARGE)
                .withType(ProtectionType.MASK_RESPIRATOR)
                .build();

            assertEquals("FPT-P20-MR-AFL", maskRespirator.getSku().getSkuCode());
            
            FaceProtection faceShield = FaceProtectionTestBuilder.aFaceProtection()
                .withType(ProtectionType.FACE_SHIELD)
                .withAntiFog(false)
                .withPackagingType(PackagingType.INDIVIDUAL)
                .build();

            assertEquals("FPT-INDV-FS-CU", faceShield.getSku().getSkuCode());
        }
        
        @Test
        void shouldGenerateSkuCorrectly_WhenProductIsALabCoat() {
            LabCoat labCoat = LabCoatTestBuilder.aLabCoat()
                .withGrammage(40)
                .withFabricType(FabricType.POLYPROPYLENE)
                .build();

            assertEquals("COAT-P20-PP40-U", labCoat.getSku().getSkuCode());

            LabCoat labCoat2 = LabCoatTestBuilder.aLabCoat()
                .withGrammage(160)
                .withFabricType(FabricType.COTTON_100)
                .build();

            assertEquals("COAT-P20-CT160-U", labCoat2.getSku().getSkuCode());
        }

        @Test
        @BeforeAll
        void shouldThrowException_WhenProductIsAUnknowSubclass() {
            SkuGenerationException skuException = assertThrows(SkuGenerationException.class, () -> {
                DummyPpe.builder().build().getSku();
            });

            assertEquals("Product with unknow type of 'Personal Protective Equipment' (PPE). Generation of SKU code is unavailable for this subclass.\n Unknow type provided: DummyPpe", skuException.getMessage());
        }
    }
    
    @Nested
    @TestInstance(Lifecycle.PER_CLASS)
    class WhenSampleContainer {
        
        @Test
        void shouldGenerateSkuCorrectly_WhenProductIsASampleBag() {
            SampleBag sampleBag = SampleBagTestBuilder.aSampleBag()
                .withCapacityMilliLiters(200)
                .withFilter(FilterType.FULL_PAGE)
                .withStandUp(true)
                .withIdentificationTag(true)
                .build();
                
            assertEquals("BAG-P20-F200-IDU", sampleBag.getSku().getSkuCode());

            SampleBag sampleBag2 = SampleBagTestBuilder.aSampleBag()
                .withCapacityMilliLiters(125)
                .withPackagingType(PackagingType.BOX)
                .withQuantityPerPackage(70)
                .withFilter(FilterType.LATERAL)
                .withStandUp(false)
                .withIdentificationTag(false)
                .build();

            assertEquals("BAG-BX70-L125-AND", sampleBag2.getSku().getSkuCode());
        }
        
        @Test
        void shouldGenerateSkuCorrectly_WhenProductIsAPetriDish() {
            PetriDish petriDish = PetriDishTestBuilder.aPetriDish()
                .withDiameterMm(90)
                .withHeightMm(15)
                .withMaterial(Material.BOROSILICATE_GLASS)
                .build();
            
            assertEquals("PTD-P20-90x15-BG", petriDish.getSku().getSkuCode());

                PetriDish petriDish2 = PetriDishTestBuilder.aPetriDish()
                .withDiameterMm(60)
                .withHeightMm(15)
                .withMaterial(Material.PP)
                .build();

            assertEquals("PTD-P20-60x15-PP", petriDish2.getSku().getSkuCode());
        }
        
        @Test
        void shouldGenerateSkuCorrectly_WhenProductIsATestTube() {
            TestTube testTube = TestTubeTestBuilder.aTestTube()
                .withMaterial(Material.PC)
                .withBottomType(BottomType.ROUND)
                .withHeightMm(100)
                .withDiameterMm(12)
                .build();
            
            assertEquals("TUB-P20-PC100x12-SR", testTube.getSku().getSkuCode());
            
            TestTube testTube2 = TestTubeTestBuilder.aTestTube()
                .withMaterial(Material.GLASS)
                .withBottomType(BottomType.CONICAL)
                .withSterilizationMethod(SterilizationMethod.NO_STERILE)
                .withHeightMm(150)
                .withDiameterMm(20)
                .build();

            assertEquals("TUB-P20-GL150x20-NC", testTube2.getSku().getSkuCode());
        }
        
        @Test
        @BeforeAll
        void shouldThrowException_WhenProductIsAUnknowSubclass() {
            SkuGenerationException skuException = assertThrows(SkuGenerationException.class, () -> {
                DummySampleContainer.builder().build().getSku();
            });

            assertEquals("Product with unknow type of 'Sample Container'. Generation of SKU code is unavailable for this subclass.\n Unknow type provided: DummySampleContainer", skuException.getMessage());
        }
    }
}
