package br.com.biosecure.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import br.com.biosecure.model.SampleContainer.ClosingMethod;
import br.com.biosecure.model.SampleContainer.Material;
import br.com.biosecure.builders.PetriDishTestBuilder;

class PetriDishTest {
    
    @Test
    void shouldBuildValidPetriDish() {
        PetriDish aPetriDish = PetriDishTestBuilder.aPetriDish()
            .withClosingMethod(ClosingMethod.SCREW_CAP_RING)
            .withDiameterMm(75)
            .withHeightMm(12)
            .withDivNum(3)
            .withGrid(true)
            .withMaterial(Material.BOROSILICATE_GLASS)
            .build();

        assertNotNull(aPetriDish);
    }

    @Test
    void shouldThrowException_WhenDivNumberIsInvalid() {
        InvalidProductAttributeException exception = assertThrows(InvalidProductAttributeException.class, () -> {
            PetriDishTestBuilder.aPetriDish().withDivNum(0).build();
        });

        assertEquals("divisions number", exception.getInvalidAttribute());
        
        InvalidProductAttributeException exception2 = assertThrows(InvalidProductAttributeException.class, () -> {
            PetriDishTestBuilder.aPetriDish().withDivNum(5).build();
        });

        assertEquals("divisions number", exception2.getInvalidAttribute());
    }

    @Test
    void shouldThrowException_WhenPhysicalDimensionsIsInvalid() {
        InvalidProductAttributeException exception = assertThrows(InvalidProductAttributeException.class, () -> {
            PetriDishTestBuilder.aPetriDish().withDiameterMm(0).withHeightMm(0).build();
        });

        assertEquals("[height (mm), width (mm)]", exception.getInvalidAttribute());
        
        InvalidProductAttributeException exception2 = assertThrows(InvalidProductAttributeException.class, () -> {
            PetriDishTestBuilder.aPetriDish().withDiameterMm(1000).withHeightMm(1000).build();
        });

        assertEquals("[height (mm), width (mm)]", exception2.getInvalidAttribute());
    }

    @Test
    void shouldCalculateNominalCapacityCorrectly() {
        PetriDish aPetri = PetriDishTestBuilder.aPetriDish()
            .withDiameterMm(100)
            .withHeightMm(10)
            .build();

        assertEquals(39.27, PetriDish.calculateNominalCapacity(aPetri.getDiameterMm(), aPetri.getHeightMm()));
        
        PetriDish anotherPetri = PetriDishTestBuilder.aPetriDish()
            .withDiameterMm(90.0)
            .withHeightMm(15.0)
            .build();

        assertEquals(47.71, anotherPetri.getCapacityMilliLiters());
    }

    @Test
    void shouldCalculateSurfaceArePerDivCorrectly() {
        double delta = 0.01;
        String message = "The calculated area should be in margin of error (0.01)";

        PetriDish aPetri = PetriDishTestBuilder.aPetriDish()
            .withDiameterMm(75)
            .withDivNum(1)
            .build();

        assertEquals(4417.86, PetriDish.calculateSurfaceAreaPerDiv(aPetri.getDiameterMm(), aPetri.getDivisionsNumber()), delta, message);

        PetriDish anotherPetri = PetriDishTestBuilder.aPetriDish()
            .withDiameterMm(100)
            .withDivNum(2) 
            .build();

        assertEquals(3926.99, anotherPetri.getSurfaceAreaPerDivision(), delta, message);
        
        PetriDish anotherPetri2 = PetriDishTestBuilder.aPetriDish()
            .withDiameterMm(90.0)
            .withDivNum(3)
            .build();
                    
        assertEquals(2120.57, anotherPetri2.getSurfaceAreaPerDivision(), delta, message);
        
        PetriDish anotherPetri3 = PetriDishTestBuilder.aPetriDish()
            .withDiameterMm(120.0)
            .withDivNum(4)
            .build();
                    
        assertEquals(2827.43, anotherPetri3.getSurfaceAreaPerDivision(), delta, message);
    }

    @Test
    void shouldThrowException_WhenCalculatesSurfaceAreaPerDiv_GivenDiameterOrDivNumIsInvalid() {
        InvalidProductAttributeException divException = assertThrows(InvalidProductAttributeException.class, () -> {
            PetriDish.calculateSurfaceAreaPerDiv(100, 0);
        });
        
        InvalidProductAttributeException divException2 = assertThrows(InvalidProductAttributeException.class, () -> {
            PetriDish.calculateSurfaceAreaPerDiv(90, 5);
        });

        assertEquals("divisions number", divException.getInvalidAttribute());
        assertEquals(divException.getInvalidAttribute(), divException2.getInvalidAttribute());

        InvalidProductAttributeException diameterException = assertThrows(InvalidProductAttributeException.class, () -> {
            PetriDish.calculateSurfaceAreaPerDiv(0, 1);
        });
        
        InvalidProductAttributeException diameterException2 = assertThrows(InvalidProductAttributeException.class, () -> {
            PetriDish.calculateSurfaceAreaPerDiv(1000, 1);
        });

        assertEquals("diameter (mm)", diameterException.getInvalidAttribute());
        assertEquals(diameterException.getInvalidAttribute(), diameterException2.getInvalidAttribute());
    }
}
