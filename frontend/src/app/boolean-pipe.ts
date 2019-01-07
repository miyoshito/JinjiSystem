import { Pipe, PipeTransform } from '@angular/core'

@Pipe({ name: 'toJapanese' })
export class BooleanPipe implements PipeTransform {

    transform(value: boolean): string {
        if (!value) {
            return '無'
        }
        else return '有'
    }

}


