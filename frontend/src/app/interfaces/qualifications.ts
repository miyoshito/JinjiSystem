export interface Qualifications {
        id: string
        sponsor: string
        qName: string
        examDate: Date
        examPlace: string
        results: string
        examFee: number
        extraFee: number
        coveredFee: number
        reward: number
        active: boolean
        employee_id: string
        employee_name: string
}

export class Qualifications {

    constructor(
        id: string,
        sponsor: string,
        qName: string,
        examDate: Date,
        examPlace: string,
        results: string,
        examFee: number,
        extraFee: number,
        coveredFee: number,
        reward: number,
        active: boolean,
        employee_id: string,
        employee_name: string
        ){

            this.id = id
            this.sponsor = sponsor
            this.qName = qName
            this.examDate = examDate
            this.examPlace = examPlace
            this.results = results
            this.examFee = examFee
            this.extraFee = extraFee
            this.coveredFee = coveredFee
            this.reward = reward
            this.active = active
            this.employee_id = employee_id
            this.employee_name = employee_name
        }
}
