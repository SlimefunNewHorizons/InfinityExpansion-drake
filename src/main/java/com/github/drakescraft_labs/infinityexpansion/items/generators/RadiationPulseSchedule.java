package com.github.drakescraft_labs.infinityexpansion.items.generators;

final class RadiationPulseSchedule {

    private RadiationPulseSchedule() {}

    static boolean isPulseTick(int progress) {
        return Math.floorMod(progress, 10) == 0;
    }
}
