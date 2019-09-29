import { IConseil } from 'app/shared/model/conseil.model';
import { ITraitement } from 'app/shared/model/traitement.model';
import { ISymptome } from 'app/shared/model/symptome.model';

export interface IMaladie {
  id?: number;
  nom?: string;
  description?: string;
  type?: string;
  conseils?: IConseil[];
  traitements?: ITraitement[];
  symptomes?: ISymptome[];
}

export class Maladie implements IMaladie {
  constructor(
    public id?: number,
    public nom?: string,
    public description?: string,
    public type?: string,
    public conseils?: IConseil[],
    public traitements?: ITraitement[],
    public symptomes?: ISymptome[]
  ) {}
}
