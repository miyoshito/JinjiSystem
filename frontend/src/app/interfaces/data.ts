export interface Data {
    tname: string
    tid: number
    tdesc: string
    active: boolean
}

export class Data {
    constructor(
        tname: string,
        tid: number,
        tdesc: string,
        active: boolean  
    ) {
        this.tname = tname
        this.tid = tid
        this.tdesc = tdesc
        this.active = active
    }
}
