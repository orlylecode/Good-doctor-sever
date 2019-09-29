import { ITraitement } from 'app/shared/model/traitement.model';

export interface IRemede {
  id?: number;
  nom?: string;
  composition?: string;
  possologie?: string;
  prevention?: string;
  prix?: string;
  traitements?: ITraitement[];
}

export class Remede implements IRemede {
  constructor(
    public id?: number,
    public nom?: string,
    public composition?: string,
    public possologie?: string,
    public prevention?: string,
    public prix?: string,
    public traitements?: ITraitement[]
  ) {}
}
