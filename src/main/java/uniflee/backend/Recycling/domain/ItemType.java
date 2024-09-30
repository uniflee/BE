package uniflee.backend.Recycling.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ItemType {

    IRON_CAN(7, "철캔", "내용물을 깨끗이 비우고, 라벨 및 뚜껑을 분리하여 배출하세요."),
    ALUMINUM_CAN(7, "알루미늄캔", "깨끗이 씻은 후, 알루미늄과 다른 재질이 섞이지 않도록 분리하세요."),
    PAPER(3, "종이", "종이에 붙은 테이프나 스티커 등을 제거하고 배출하세요."),
    COLORLESS_PET(6, "무색 단일", "내용물을 비우고, 라벨을 제거한 후 배출하세요."),
    COLORED_PET(4, "유색 단일", "병을 깨끗이 씻고, 라벨을 제거한 후 배출하세요."),
    PE(5, "PE", "깨끗이 세척한 후 비닐과 함께 배출하세요."),
    PP(5, "PP", "PP 플라스틱은 세척 후 플라스틱으로 배출하세요."),
    PS(5, "PS", "내용물을 비우고, 이물질을 제거한 후 배출하세요."),
    STYROFOAM(6, "스티로폼", "깨끗한 상태로 배출하며, 스티커나 테이프는 제거하세요."),
    VINYL(5, "비닐", "깨끗하게 세척 후 비닐류로 배출하세요."),
    BROWN_GLASS(4, "갈색 유리병", "내용물을 비우고, 뚜껑을 분리한 후 배출하세요."),
    GREEN_GLASS(4, "녹색 유리병", "내용물을 비우고, 뚜껑을 분리한 후 배출하세요."),
    CLEAR_GLASS(4, "투명 유리병", "내용물을 비우고, 뚜껑을 분리한 후 배출하세요.");

    private final int points;
    private final String displayName;
    private final String disposalInstructions;  // 분리 배출법

}
