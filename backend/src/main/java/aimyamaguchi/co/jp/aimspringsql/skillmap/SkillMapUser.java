package aimyamaguchi.co.jp.aimspringsql.skillmap;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class SkillMapUser {

    private String id;

    private String name;

    private String affiliations;

    private List<SkillMapUtil> map = new ArrayList<>();

}
