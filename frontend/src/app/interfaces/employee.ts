import { Data } from "./data";
import { Resume } from "src/app/interfaces/resume-details-interface";
import { studyCourse } from "./study-course"
import { affiliation } from "../admin/employee-master/employee-master.component";


export interface MinEmployee {
    id: string
    fullName: string
    group: group[]
    admin: boolean
}
export interface group{
    id: number
    desc: string
}

export interface Employee {
    shainId: string
    shainPassword: string
    shainName: string
    role: Roles
    shainRecruit: string
    shainKana: string
    shainBirthday: String
    shainBloodType: string
    shainSex: string
    position: Data //yaku-interface
    shainSupport: boolean
    shainMarried: boolean
    shainHomePhoneNumber: string
    shainMobilePhoneNumber: string
    shainMail: string
    shainMobileMail: string
    shainPostalCode: string
    shainAddress: string
    shainArea: Data //area-interface
    shainJoinedDate: String
    shainRetiredDate: String
    shainRetired: boolean,
    shainCarModel: string,
    shainNotes: string,
    shainRegisterDate: String,
    shainRegisteredBy: string,
    shainDeletedFlag: boolean,

    affiliation: affiliation
    resume: Resume
    curriculum: Curriculum[]
    educations: studyCourse[]
}

export interface Roles{
    roleid: number
    authority: string
    roledesc: string
}

export interface Curriculum{
    id: number
    startdate: Date
    enddate: Date
    experienceTime: number
    customer: string
    active: boolean
    deleted: boolean
    industryType: String
    industryClass: String
    industryTypeId: number
    industryClassId: number
    targetBusiness: string
    assignData: DataCommons
    makerData: DataCommons[]
    osData: DataCommons[]
    dbmsData: DataCommons[]
    responseData: DataCommons[]
    langData: DataCommons[]
    toolsData: DataCommons[]
}

export interface DataCommons{
    id: number
    desc: string
    active: boolean
}

export interface Industry{
    id: number
    desc: string
    active: boolean

}
export interface IndustryClass{
    indId: Industry
    id: number
    desc: string
    active: boolean

}
/*
export class Employee {
    constructor(
        shainId: string,
        shainPassword: string,
        shainName: string,
        shainRecruit: string,
        shainKana: string,
        shainBirthday: String,
        shainBloodType: string,
        shainSex: string,
        position: Data, //yaku-interface
        shainSupport: boolean,
        shainMarried: boolean,
        shainHomePhoneNumber: string,
        shainMobilePhoneNumber: string,
        shainMail: string,
        shainMobileMail: string,
        shainPostalCode: string,
        shainAddress: string,
        shainArea: Data, //area-interface
        shainJoinedDate: String,
        shainRetiredDate: String,
        shainActive: boolean,
        shainCarModel: string,
        shainNotes: string,
        shainRegisterDate: String,
        shainRegisteredBy: string,
        shainDeletedFlag: boolean,
        role: Roles
        ){

            this.shainId = shainId
            this.shainPassword= shainPassword,
            this.shainName= shainName,
            this.shainRecruit= shainRecruit,
            this.shainKana= shainKana,
            this.shainBirthday= shainBirthday,
            this.shainBloodType= shainBloodType,
            this.shainSex= shainSex,
            this.position = position, //yaku-interface
            this.shainSupport= shainSupport,
            this.shainMarried= shainMarried,
            this.shainHomePhoneNumber= shainHomePhoneNumber,
            this.shainMobilePhoneNumber= shainMobilePhoneNumber,
            this.shainMail= shainMail,
            this.shainMobileMail= shainMobileMail,
            this.shainPostalCode= shainPostalCode,
            this.shainAddress= shainAddress,
            this.shainArea = shainArea, //area-interface
            this.shainJoinedDate= shainJoinedDate,
            this.shainRetiredDate= shainRetiredDate,
            this.shainActive= shainActive,
            this.shainCarModel= shainCarModel,
            this.shainNotes= shainNotes,
            this.shainRegisterDate= shainRegisterDate,
            this.shainRegisteredBy= shainRegisteredBy,
            this.shainDeletedFlag= shainDeletedFlag
            this.role = role
        }
    }*/
