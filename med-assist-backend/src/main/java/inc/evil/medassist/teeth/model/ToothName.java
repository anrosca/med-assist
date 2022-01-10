package inc.evil.medassist.teeth.model;

import lombok.Getter;

@Getter
public enum ToothName {
    UR1(1, "upper right central incisor", 21),
    UR2(2, "upper right lateral incisor", 22),
    UR3(3, "upper right cuspid", 23),
    UR4(4, "upper right first bicuspid", 24),
    UR5(5, "upper right second bicuspid", 25),
    UR6(6, "upper right first molar", 26),
    UR7(7, "upper right second molar", 27),
    UR8(8, "upper right third molar", 28),
    UL1(1, "upper left central incisor", 11),
    UL2(2, "upper left lateral incisor", 12),
    UL3(3, "upper left cuspid", 13),
    UL4(4, "upper left first bicuspid", 14),
    UL5(5, "upper left second bicuspid", 15),
    UL6(6, "upper left first molar", 16),
    UL7(7, "upper left second molar", 17),
    UL8(8, "upper left third molar", 18),
    LL1(1, "lower left central incisor", 41),
    LL2(2, "lower left lateral incisor", 42),
    LL3(3, "lower left cuspid", 43),
    LL4(4, "lower left first bicuspid", 44),
    LL5(5, "lower left second bicuspid", 45),
    LL6(6, "lower left first molar", 46),
    LL7(7, "lower left second molar", 47),
    LL8(8, "lower left third molar", 48),
    LR1(1, "lower right central incisor", 31),
    LR2(2, "lower right lateral incisor", 32),
    LR3(3, "lower right cuspid", 33),
    LR4(4, "lower right first bicuspid", 34),
    LR5(5, "lower right second bicuspid", 35),
    LR6(6, "lower right first molar", 36),
    LR7(7, "lower right second molar", 37),
    LR8(8, "lower right third molar", 38);

    private final int number;
    private final String scientificName;
    private final int numericCode;

    ToothName(final int number, final String scientificName, final int numericCode) {
        this.number = number;
        this.scientificName = scientificName;
        this.numericCode = numericCode;
    }
}
