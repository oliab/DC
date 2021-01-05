import { IUser } from 'app/core/user/user.model';
import { ICatEstado } from 'app/shared/model/cat-estado.model';

export interface ICatMunicipio {
  id?: number;
  nombre?: string;
  clave?: string;
  usuario?: IUser;
  estado?: ICatEstado;
}

export class CatMunicipio implements ICatMunicipio {
  constructor(public id?: number, public nombre?: string, public clave?: string, public usuario?: IUser, public estado?: ICatEstado) {}
}
