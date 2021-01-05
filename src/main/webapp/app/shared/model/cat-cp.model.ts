import { IUser } from 'app/core/user/user.model';
import { ICatEstado } from 'app/shared/model/cat-estado.model';
import { ICatMunicipio } from 'app/shared/model/cat-municipio.model';
import { ICatRiesgo } from 'app/shared/model/cat-riesgo.model';

export interface ICatCP {
  id?: number;
  anio?: number;
  usuario?: IUser;
  estado?: ICatEstado;
  municipio?: ICatMunicipio;
  riesgo?: ICatRiesgo;
}

export class CatCP implements ICatCP {
  constructor(
    public id?: number,
    public anio?: number,
    public usuario?: IUser,
    public estado?: ICatEstado,
    public municipio?: ICatMunicipio,
    public riesgo?: ICatRiesgo
  ) {}
}
