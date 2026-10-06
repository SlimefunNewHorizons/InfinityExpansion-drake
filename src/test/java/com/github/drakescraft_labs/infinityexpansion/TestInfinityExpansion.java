package com.github.drakescraft_labs.infinityexpansion.items.generators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InfinityReactorTest {

    @Test
    void radiationPulseKeepsTheConfiguredTenTickCadence() {
        assertTrue(RadiationPulseSchedule.isPulseTick(0));
        assertTrue(RadiationPulseSchedule.isPulseTick(10));
        assertTrue(RadiationPulseSchedule.isPulseTick(-10));
        assertFalse(RadiationPulseSchedule.isPulseTick(1));
        assertFalse(RadiationPulseSchedule.isPulseTick(9));
    }

}
