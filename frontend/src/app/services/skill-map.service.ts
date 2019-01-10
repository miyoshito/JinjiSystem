import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SkillMapService {

  constructor() { }

/*
  getSkillMaps(id?: string, name?: string, sh?:string, kt?:string){

    (!id) ? id = '' : id;
    (!name) ? name = '' : name;
    (!sh) ? sh = '' : sh;
    (!kt) ? kt = '' : kt;

    this._http.get<SkillMapData[]>(API_URL + '/api/se/skillmapbuilder?'
    + 'id=' + id
    + '&nm=' + name
    + '&sh=' + sh
    + '&kt=' + kt
    , {
      observe: 'response'
    }).subscribe(res =>{
      if(!res.body.length){
        alert('結果が見つかりません')
        return
      } else this.buildMapSource_.next(res.body)
    })
  }
*/
}
