import { IRemede } from 'app/shared/model/remede.model';
import { IMaladie } from 'app/shared/model/maladie.model';

export interface ITraitement {
  id?: number;
  nom?: string;
  auteur?: string;
  description?: string;
  remedes?: IRemede[];
  maladies?: IMaladie[];
}

export class Traitement implements ITraitement {
  constructor(
    public id?: number,
    public nom?: string,
    public auteur?: string,
    public description?: string,
    public remedes?: IRemede[],
    public maladies?: IMaladie[]
  ) {}
}
