import { IMaladie } from 'app/shared/model/maladie.model';

export interface IConseil {
  id?: number;
  nom?: string;
  auteur?: string;
  conseil?: string;
  maladies?: IMaladie[];
}

export class Conseil implements IConseil {
  constructor(public id?: number, public nom?: string, public auteur?: string, public conseil?: string, public maladies?: IMaladie[]) {}
}
