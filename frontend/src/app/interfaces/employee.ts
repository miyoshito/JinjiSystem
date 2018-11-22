import { Data } from "./data";

export interface Employee {
    shainId: number
    shainPassword: string
    shainName: string
    role: Roles
    shainRecruit: string
    shainKana: string
    shainBirthday: Date
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
    shainJoinedDate: Date
    shainRetiredDate: Date
    shainActive: boolean,
    shainCarModel: string,
    shainNotes: string,
    shainRegisterDate: Date,
    shainRegisteredBy: string,
    shainDeletedFlag: boolean,
}

export interface Roles{
    roleid: number
    authority: string
    roledesc: string
}

export class Employee {
    constructor(
        shainId: number,
        shainPassword: string,
        shainName: string,
        shainRecruit: string,
        shainKana: string,
        shainBirthday: Date,
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
        shainJoinedDate: Date,
        shainRetiredDate: Date,
        shainActive: boolean,
        shainCarModel: string,
        shainNotes: string,
        shainRegisterDate: Date,
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
}
