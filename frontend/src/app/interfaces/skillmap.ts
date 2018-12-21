export interface SkillMapData{
    id: string
    name: string
    katakana: string
    affiliation: string
    params: Map<String, SkillMapParams[]>
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
        params: Map<String, SkillMapParams[]>
    ){
        this.id = id
        this.name = name
        this.katakana = katakana
        this.affiliation = affiliation
        this.params = params
    }

}