import { Moment } from 'moment';
import { ICatNacionalidad } from 'app/shared/model/cat-nacionalidad.model';
import { ICatPais } from 'app/shared/model/cat-pais.model';
import { ICatEstado } from 'app/shared/model/cat-estado.model';
import { ICatMunicipio } from 'app/shared/model/cat-municipio.model';
import { ICatLocalidad } from 'app/shared/model/cat-localidad.model';
import { ICatCP } from 'app/shared/model/cat-cp.model';
import { IUser } from 'app/core/user/user.model';

export interface IDomicilioUsuario {
  id?: number;
  colonia?: string;
  calle?: string;
  noExt?: string;
  noInt?: string;
  domicilio?: string;
  fechaAct?: Moment;
  nacionalidad?: ICatNacionalidad;
  paisOrigen?: ICatPais;
  pais?: ICatPais;
  estado?: ICatEstado;
  municipio?: ICatMunicipio;
  localidad?: ICatLocalidad;
  cp?: ICatCP;
  user?: IUser;
}

export class DomicilioUsuario implements IDomicilioUsuario {
  constructor(
    public id?: number,
    public colonia?: string,
    public calle?: string,
    public noExt?: string,
    public noInt?: string,
    public domicilio?: string,
    public fechaAct?: Moment,
    public nacionalidad?: ICatNacionalidad,
    public paisOrigen?: ICatPais,
    public pais?: ICatPais,
    public estado?: ICatEstado,
    public municipio?: ICatMunicipio,
    public localidad?: ICatLocalidad,
    public cp?: ICatCP,
    public user?: IUser
  ) {}
}

export class DomicilioUsuarioDTO {
  colonia?: string;
  calle?: string;
  noExt?: string;
  noInt?: string;
  domicilio?: string;
  fechaAct?: Moment;
  nacionalidadId?: number;
  paisOrigenId?: string;
  paisId?: string;
  estadoId?: string;
  municipioId?: string;
  localidadId?: string;
  cpId?: string;
  nacionalidad?: ICatNacionalidad;
  paisOrigen?: ICatPais;
  pais?: ICatPais;
  estado?: ICatEstado;
  municipio?: ICatMunicipio;
  localidad?: ICatLocalidad;
  cp?: ICatCP;
}
