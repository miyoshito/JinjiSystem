export interface Data {
    tname: string
    id: number
    desc: string
    active: boolean
}

export class Data {
    constructor(
        tname: string,
        id: number,
        desc: string,
        active: boolean  
    ) {
        this.tname = tname
        this.id = id
        this.desc = desc
        this.active = active
    }
}
