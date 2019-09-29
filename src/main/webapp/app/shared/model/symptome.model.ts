import { IMaladie } from 'app/shared/model/maladie.model';

export interface ISymptome {
  id?: number;
  nom?: string;
  description?: string;
  effet?: string;
  maladies?: IMaladie[];
}

export class Symptome implements ISymptome {
  constructor(public id?: number, public nom?: string, public description?: string, public effet?: string, public maladies?: IMaladie[]) {}
}
