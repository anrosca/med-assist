package inc.evil.medassist.teeth.model;

import lombok.Getter;

@Getter
public enum ToothName {
    UR1(1, "upper right central incisor"),
    UR2(2, "upper right lateral incisor"),
    UR3(3, "upper right cuspid"),
    UR4(4, "upper right first bicuspid"),
    UR5(5, "upper right second bicuspid"),
    UR6(6, "upper right first molar"),
    UR7(7, "upper right second molar"),
    UR8(8, "upper right third molar"),
    UL1(1, "upper left central incisor"),
    UL2(2, "upper left lateral incisor"),
    UL3(3, "upper left cuspid"),
    UL4(4, "upper left first bicuspid"),
    UL5(5, "upper left second bicuspid"),
    UL6(6, "upper left first molar"),
    UL7(7, "upper left second molar"),
    UL8(8, "upper left third molar"),
    LL1(1, "lower left central incisor"),
    LL2(2, "lower left lateral incisor"),
    LL3(3, "lower left cuspid"),
    LL4(4, "lower left first bicuspid"),
    LL5(5, "lower left second bicuspid"),
    LL6(6, "lower left first molar"),
    LL7(7, "lower left second molar"),
    LL8(8, "lower left third molar"),
    LR1(1, "lower right central incisor"),
    LR2(2, "lower right lateral incisor"),
    LR3(3, "lower right cuspid"),
    LR4(4, "lower right first bicuspid"),
    LR5(5, "lower right second bicuspid"),
    LR6(6, "lower right first molar"),
    LR7(7, "lower right second molar"),
    LR8(8, "lower right third molar");

    private final int number;
    private final String scientificName;

    ToothName(final int number, final String scientificName) {
        this.number = number;
        this.scientificName = scientificName;
    }
}
