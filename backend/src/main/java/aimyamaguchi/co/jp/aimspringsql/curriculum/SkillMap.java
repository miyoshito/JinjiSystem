package aimyamaguchi.co.jp.aimspringsql.curriculum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillMap {

    String id;
    String name;
    String affiliation;

    //LocalDate updated;

    //Map<String, Map<String,SkillMapUtil>> params;
    Map<String, List<SkillMapUtil>> params;

}
