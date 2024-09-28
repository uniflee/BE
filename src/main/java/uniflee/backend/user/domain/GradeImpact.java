package uniflee.backend.user.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@AllArgsConstructor
public enum GradeImpact {
    BRONZE("5-10 그루", "100 kWh", "10 kg", "100 kg"),
    SILVER("7-15 그루", "150 kWh", "12 kg", "150 kg"),
    GOLD("10-20 그루", "200 kWh", "15 kg", "200 kg"),
    PLATINUM("12-25 그루", "250 kWh", "18 kg", "250 kg"),
    DIAMOND("15-30 그루", "300 kWh", "20 kg", "300 kg");

    private final String treesProtected;
    private final String energySaved;
    private final String plasticPrevented;
    private final String co2Reduced;
}
