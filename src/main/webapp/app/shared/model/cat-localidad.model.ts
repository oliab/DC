import { IUser } from 'app/core/user/user.model';
import { ICatEstado } from 'app/shared/model/cat-estado.model';
import { ICatMunicipio } from 'app/shared/model/cat-municipio.model';

export interface ICatLocalidad {
  id?: number;
  nombre?: string;
  clave?: string;
  usuario?: IUser;
  estado?: ICatEstado;
  municipio?: ICatMunicipio;
}

export class CatLocalidad implements ICatLocalidad {
  constructor(
    public id?: number,
    public nombre?: string,
    public clave?: string,
    public usuario?: IUser,
    public estado?: ICatEstado,
    public municipio?: ICatMunicipio
  ) {}
}
