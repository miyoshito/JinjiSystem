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

export interface cvForm{
    id: string,
    name: string,
    kana: string,
    recruit: string,
    age: string
    operator: string
    experience: string
    customerName: string,
    indType: string,
    targetBusiness: string,
    dbms: number[]
    os: number[]
    lang: number[]
    tools: number[]
    response: number[]
    maker: number[]
    role: string
  }

  export interface SkillMap {
    DBMS: Childs[]
    OS: Childs[]
    DUTY: Childs[]
    ASSIGN: Childs[]
    LANG: Childs[]
    MAKER: Childs[]
    TOOLS: Childs[]
  }

  export interface Childs {
    id: number
    desc: string
    active: boolean
  }

  export interface IndustryClass {
    id: number
    desc: string
  }
  
  export interface IndustryType {
    id: number
    desc: string
    class: IndustryClass[]
  }
  
