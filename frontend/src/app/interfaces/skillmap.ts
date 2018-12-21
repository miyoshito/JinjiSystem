export interface SkillMapData{
    id: string
    name: string
    katakana: string
    affiliation: string

    lang: SkillMapParams[]
    os: SkillMapParams[]
    db: SkillMapParams[]
    tools: SkillMapParams[]
    maker: SkillMapParams[]
    duty: SkillMapParams[]
    industry: SkillMapParams[]
}

export interface SkillMapParams {    
    description: string
    experience: number
}
export class SkillMapParams{
    constructor(
        description: string,
        experience: number
    ){
        this.description = description
        this.experience = experience
    }
}

export class SkillMapData{

    constructor(
        id: string,
        name: string,
        katakana: string,
        affiliation: string,
        lang: SkillMapParams[],
        os: SkillMapParams[],
        db: SkillMapParams[],
        tools: SkillMapParams[],
        maker: SkillMapParams[],
        duty: SkillMapParams[],
        industry: SkillMapParams[]
    ){
        this.id = id
        this.name = name
        this.katakana = katakana
        this.affiliation = affiliation
        this.lang = lang
        this.os = os
        this.db = db
        this.tools = tools
        this.maker = maker
        this.duty = duty
        this.industry = industry
    }

}