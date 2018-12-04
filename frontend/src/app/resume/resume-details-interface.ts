import { Optional } from "@angular/core";

export interface Resume {

    resumeId: number        
    universityName: string
    formation: string
    bunri: string 
    careers: Career[]
    qualifications: Qualifications[]
    commendations: Commendations[]
}
export class Resume {
    constructor(
        resumeId: number,
        universityName: string,
        formation: string,
        bunri: string,
        @Optional() careers: Career[],
        @Optional() qualifications: Qualifications[],
        @Optional() commendations: Commendations[]
    ){
        this.resumeId = resumeId
        this.universityName = universityName
        this.formation = formation
        this.careers = careers
        this.qualifications = qualifications
        this.commendations = commendations
        this.bunri = bunri
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

export interface Qualifications{
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
