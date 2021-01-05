import { IUser } from 'app/core/user/user.model';

export interface ICatRiesgo {
  id?: number;
  riesgo?: string;
  usuario?: IUser;
}

export class CatRiesgo implements ICatRiesgo {
  constructor(public id?: number, public riesgo?: string, public usuario?: IUser) {}
}
