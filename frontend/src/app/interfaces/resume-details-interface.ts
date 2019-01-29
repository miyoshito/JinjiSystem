import { Optional } from "@angular/core";

export interface Resume {
    resumeId: number        
    universityName: string
    formation: string
    bunri: string
    careers: Career[]
    qualifications: rQualifications[]
    commendations: Commendations[]
    employee: miniEmployee
    notes: string
}

export interface miniEmployee{
    id: string
}
export class Resume {
    constructor(
        resumeId: number,
        universityName: string,
        formation: string,
        bunri: string,
        employee: miniEmployee,
        @Optional() careers: Career[],
        @Optional() qualifications: rQualifications[],
        @Optional() commendations: Commendations[],
        notes: string
    ){
        this.resumeId = resumeId
        this.universityName = universityName
        this.formation = formation
        this.careers = careers
        this.qualifications = qualifications
        this.commendations = commendations
        this.bunri = bunri
        this.employee = employee
        this.notes = notes
    }
}

export interface Career {
    careerid: number,
    career_year: number,
    career_month: number,
    career_scwk: string,
    career_dpaf: string,
    career_result: string
    active: boolean
}
export class Career {
    constructor(
        careerid: number,
        career_year: number,
        career_month: number,
        career_scwk: string,
        career_dpaf: string,
        career_result: string,
        active: boolean
    ){
        this.careerid = careerid
        this.career_year = career_year
        this.career_month = career_month
        this.career_scwk = career_scwk
        this.career_dpaf = career_dpaf
        this.career_result = career_result
        this.active = active
    }
}

export interface rQualifications{
    qualificationid: number,
    qualification_year: number,
    qualification_month: number,
    qualification_name: string,
    qualification_result: string,
    active: boolean 
}

export interface Commendations{
    commendationid: number,
    commendation_year: number,
    commendation_month: number,
    commendation_name: string,
    commendation_result: string,
    active: boolean
}

export interface SearchForm{
    id: string,
    name: string,
    kana: string,
    recruit: string,
    age: string,
    study: string,
    school: string,
    bunri: string,
    career: string,
    qualification: string
}