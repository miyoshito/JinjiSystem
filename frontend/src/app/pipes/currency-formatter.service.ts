import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CurrencyFormatterService {

  constructor() { }

  currencyFormat(value: any): string {
    let num: number
    let strtoint: string
    if (typeof value === 'string') {
      strtoint = value.replace(",", "").replace("￥", "").replace(".", "")
      num = parseInt(strtoint)      
      let mask: string
      mask = new Intl.NumberFormat('ja-JP', { style: 'currency', currency: 'JPY' }).format(num);
      return mask
    } else if (typeof value === 'number') {
      let mask: string
      mask = new Intl.NumberFormat('ja-JP', { style: 'currency', currency: 'JPY' }).format(value);
      return mask
    } else {
      let mask: string
      mask = new Intl.NumberFormat('ja-JP', { style: 'currency', currency: 'JPY' }).format(0);
      return mask
    }
  }

  spRemove(value: string): number {
    let formatted: number
    formatted = parseInt(value.replace(",", "").replace("￥", ""))
    return formatted
  }



}
