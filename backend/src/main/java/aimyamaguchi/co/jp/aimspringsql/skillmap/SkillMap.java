package aimyamaguchi.co.jp.aimspringsql.skillmap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillMap {

    String id;
    String name;
    String affiliation;
    Map<String, List<SkillMapUtil>> params;

}
