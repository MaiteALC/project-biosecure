package br.com.biosecure.model.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.OptionalDouble;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import br.com.biosecure.builders.*;
import br.com.biosecure.model.product.CultureMedia.Presentation;
import br.com.biosecure.model.product.CultureMedia.QuantificationUnit;
import br.com.biosecure.model.product.CultureMedia.StorageConditions;
import br.com.biosecure.model.product.FaceProtection.ProtectionType;
import br.com.biosecure.model.product.Glove.GloveMaterial;
import br.com.biosecure.model.product.LabCoat.FabricType;
import br.com.biosecure.model.product.PPE.Size;
import br.com.biosecure.model.product.Product.MeasureUnit;
import br.com.biosecure.model.product.Product.PackagingType;
import br.com.biosecure.model.product.SampleBag.FilterType;
import br.com.biosecure.model.product.SampleContainer.Material;
import br.com.biosecure.model.product.SampleContainer.SterilizationMethod;
import br.com.biosecure.model.product.Sanitizer.ChemicalBase;
import br.com.biosecure.model.product.Sanitizer.PhysicalForm;
import br.com.biosecure.model.product.TestTube.BottomType;

@TestInstance(Lifecycle.PER_CLASS)
public class SkuTest {

    @Test
    @BeforeAll
    public void shouldThrowException_WhenProductIsInvalid() {
        SkuGenerationException skuException = assertThrows(SkuGenerationException.class, () -> {
            ProductBuilder.aProduct().build().getSku();
        });

        NullPointerException skuExceptionForNull = assertThrows(NullPointerException.class, () -> {
            new SKU(null);
        });

        assertEquals("Unknow product type. Generation of SKU code is unavailable for this subclass.\n Unknow type provided: ProductDummy", skuException.getMessage());

        assertEquals("The product mustn't be null.", skuExceptionForNull.getMessage());
    }
    
    @Test
    public void shouldGenerateSkuCorrectly_WhenProductIsASanitizer() {
        Sanitizer sanitizerAmmonium = SanitizerBuilder.aSanitizer()
            .withQuantityPerPackage(5)
            .withMeasureUnit(MeasureUnit.L)
            .withPackagingType(PackagingType.GALLON)
            .withActiveIngredient(ChemicalBase.QUATERNARY_AMMONIUM)
            .withFlammable(false)
            .withForm(PhysicalForm.LIQUID)
            .build();
        
        assertEquals("SAN-G5L-QA-LQNF", sanitizerAmmonium.getSku().getSkuCode());

        Sanitizer sanitizerAlcohol = SanitizerBuilder.aSanitizer()
            .withQuantityPerPackage(1)
            .withMeasureUnit(MeasureUnit.L)
            .withPackagingType(PackagingType.BOTTLE)
            .withActiveIngredient(ChemicalBase.ALCOHOL_ISOPROPYL)
            .withFlammable(true)
            .withForm(PhysicalForm.LIQUID)
            .build();

        assertEquals("SAN-BT1L-AI-LQFL", sanitizerAlcohol.getSku().getSkuCode());
    }
    
    @Test
    public void shouldGenerateSkuCorrectly_WhenProductIsACultureMedia() {
        CultureMedia cultureMedia = CultureMediaBuilder.aCultureMediaBuilder()
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

        CultureMedia cultureMedia2 = CultureMediaBuilder.aCultureMediaBuilder()
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
        public void shouldGenerateSkuCorrectly_WhenProductIsAGlove() {
            Glove glove = GloveBuilder.aGlove()
                .withMaterial(GloveMaterial.NITRILE)
                .withLongBarrel(false)
                .withThicknessMils(6)
                .build();
                
            assertEquals("GLV-P20-6NT-SU", glove.getSku().getSkuCode());

            Glove glove2 = GloveBuilder.aGlove()
                .withMaterial(GloveMaterial.LATEX)
                .withThicknessMils(4)
                .withLongBarrel(true)
                .build();

            assertEquals("GLV-P20-4LA-LU", glove2.getSku().getSkuCode());
        }

        @Test
        public void shouldGenerateSkuCorrectly_WhenProductIsAFaceProtection() {
            FaceProtection goggles = FaceProtectionBuilder.aFaceProtection()
                .withAntiFog(false)
                .withType(ProtectionType.GOGGLES)
                .withSize(Size.MEDIUM)
                .build();
                
            assertEquals("FPT-P20-GG-CM", goggles.getSku().getSkuCode());

            FaceProtection maskRespirator = FaceProtectionBuilder.aFaceProtection()
                .withAntiFog(true)
                .withSize(Size.LARGE)
                .withType(ProtectionType.MASK_RESPIRATOR)
                .build();

            assertEquals("FPT-P20-MR-AFL", maskRespirator.getSku().getSkuCode());
            
            FaceProtection faceShield = FaceProtectionBuilder.aFaceProtection()
                .withType(ProtectionType.FACE_SHIELD)
                .withAntiFog(false)
                .withPackagingType(PackagingType.INDIVIDUAL)
                .build();

            assertEquals("FPT-INDV-FS-CU", faceShield.getSku().getSkuCode());
        }
        
        @Test
        public void shouldGenerateSkuCorrectly_WhenProductIsALabCoat() {
            LabCoat labCoat = LabCoatBuilder.aLabCoat()
                .withGrammage(40)
                .withFabricType(FabricType.POLYPROPYLEN)
                .build();

            assertEquals("COAT-P20-PP40-U", labCoat.getSku().getSkuCode());

            LabCoat labCoat2 = LabCoatBuilder.aLabCoat()
                .withGrammage(160)
                .withFabricType(FabricType.COTTON_100)
                .build();

            assertEquals("COAT-P20-CT160-U", labCoat2.getSku().getSkuCode());
        }

        @Test
        @BeforeAll
        public void shouldThrowException_WhenProductIsAUnknowSubclass() {
            SkuGenerationException skuException = assertThrows(SkuGenerationException.class, () -> {
                PpeBuider.aPPE().build().getSku();
            });

            assertEquals("Product with unknow type of 'Personal Protective Equipment' (PPE). Generation of SKU code is unavailable for this subclass.\n Unknow type provided: PersonalProtectiveEquipmentDummy", skuException.getMessage());
        }
    }
    
    @Nested
    @TestInstance(Lifecycle.PER_CLASS)
    class WhenSampleContainer {
        
        @Test
        public void shouldGenerateSkuCorrectly_WhenProductIsASampleBag() {
            SampleBag sampleBag = SampleBagBuilder.aSampleBag()
                .withCapacityMiliLiters(200)
                .withFilter(FilterType.FULL_PAGE)
                .withStandUp(true)
                .withIdentificationTag(true)
                .build();
                
            assertEquals("BAG-P20-F200-IDU", sampleBag.getSku().getSkuCode());

            SampleBag sampleBag2 = SampleBagBuilder.aSampleBag()
                .withCapacityMiliLiters(125)
                .withPackagingType(PackagingType.BOX)
                .withQuantityPerPackage(70)
                .withFilter(FilterType.LATERAL)
                .withStandUp(false)
                .withIdentificationTag(false)
                .build();

            assertEquals("BAG-BX70-L125-AND", sampleBag2.getSku().getSkuCode());
        }
        
        @Test
        public void shouldGenerateSkuCorrectly_WhenProductIsAPetriDish() {
            PetriDish petriDish = PetriDishBuilder.aPetriDish()
                .withDiameterMm(90)
                .withHeightMm(15)
                .withMaterial(Material.BOROSILICATE_GLASS)
                .build();
            
            assertEquals("PTD-P20-90x15-BG", petriDish.getSku().getSkuCode());

                PetriDish petriDish2 = PetriDishBuilder.aPetriDish()
                .withDiameterMm(60)
                .withHeightMm(15)
                .withMaterial(Material.PP)
                .build();

            assertEquals("PTD-P20-60x15-PP", petriDish2.getSku().getSkuCode());
        }
        
        @Test
        public void shouldGenerateSkuCorrectly_WhenProductIsATestTube() {
            TestTube testTube = TestTubeBuilder.aTestTube()
                .withMaterial(Material.PC)
                .withBottomType(BottomType.ROUND)
                .withHeightMm(100)
                .withDiameterMm(12)
                .build();
            
            assertEquals("TUB-P20-PC100x12-SR", testTube.getSku().getSkuCode());
            
            TestTube testTube2 = TestTubeBuilder.aTestTube()
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
        public void shouldThrowException_WhenProductIsAUnknowSubclass() {
            SkuGenerationException skuException = assertThrows(SkuGenerationException.class, () -> {
                SampleContainerBuilder.aSampleContainer().build().getSku();
            });

            assertEquals("Product with unknow type of 'Sample Container'. Generation of SKU code is unavailable for this subclass.\n Unknow type provided: SampleContainerDummy", skuException.getMessage());
        }
    }
}
