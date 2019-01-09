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

  export class cvForm {
    constructor(
    id: string,
    name: string,
    kana: string,
    recruit: string,
    age: string,
    operator: string,
    experience: string,
    customerName: string,
    indType: string,
    targetBusiness: string,
    dbms: number[],
    os: number[],
    lang: number[],
    tools: number[],
    response: number[],
    maker: number[],
    role: string
    ){
      this.id  = id
      this.name = name
      this.kana = kana
      this.recruit = recruit
      this.age = age
      this.operator = operator
      this.experience = experience
      this.customerName = customerName
      this.indType = indType
      this.targetBusiness = targetBusiness
      this.dbms = dbms
      this.os = os
      this.lang = lang
      this.tools = tools
      this.response = response
      this.maker = maker
      this.role = role}
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
  
