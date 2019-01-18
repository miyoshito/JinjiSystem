package aimyamaguchi.co.jp.aimspringsql.util;

import aimyamaguchi.co.jp.aimspringsql.AdministratorModel;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.AFFILIATIONData;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.EmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.employee.Repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdministratorService {

    @Autowired
    private SearchFilters sf;

    @Autowired
    private EmployeeRepository er;

    public List<AdministratorModel> listUsersAndPermissions(){

        List<EmployeeMaster> users = sf.getAllEmployees();

        List<AdministratorModel> admin = new ArrayList<>();

        users.stream().forEach(user ->{
            String aff = user.getAffiliation().stream().map(AFFILIATIONData::getDesc).collect(Collectors.joining("/"));
            admin.add(new AdministratorModel(user.getShainId(), user.getShainName(), aff, user.isAdmin()));
        });

        return admin;
    }

    public List<AdministratorModel> listUsersAndPermissionsWithId(String id){

        EmployeeMaster user = sf.getEmployeeData(id);
        List<AdministratorModel> admin = new ArrayList<>();
        if(user != null) {

            String aff = user.getAffiliation().stream().map(AFFILIATIONData::getDesc).collect(Collectors.joining("/"));
            admin.add(new AdministratorModel(user.getShainId(), user.getShainName(), aff, user.isAdmin()));

        }
        return admin;
    }

    public void updateUserPermissions(List<AdministratorModel> adminModel){

        adminModel.stream()
                .forEach(model ->{
                    EmployeeMaster e = sf.getEmployeeData(model.getId());
                    e.setAdmin(model.isAdmin());
                    er.save(e);
                });
    }

}
