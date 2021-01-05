import { Moment } from 'moment';
import { ICliente } from 'app/shared/model/cliente.model';
import { ICatTipoDocumento } from 'app/shared/model/cat-tipo-documento.model';
import { IUser } from 'app/core/user/user.model';

export interface IExpedienteCliente {
  id?: number;
  empresarial?: boolean;
  descripcion?: string;
  documentoContentType?: string;
  documento?: any;
  fechaAlta?: Moment;
  fechaAct?: Moment;
  cliente?: ICliente;
  tipoDocumento?: ICatTipoDocumento;
  usuarioAlta?: IUser;
  usuarioAct?: IUser;
}

export class ExpedienteCliente implements IExpedienteCliente {
  constructor(
    public id?: number,
    public empresarial?: boolean,
    public descripcion?: string,
    public documentoContentType?: string,
    public documento?: any,
    public fechaAlta?: Moment,
    public fechaAct?: Moment,
    public cliente?: ICliente,
    public tipoDocumento?: ICatTipoDocumento,
    public usuarioAlta?: IUser,
    public usuarioAct?: IUser
  ) {
    this.empresarial = this.empresarial || false;
  }
}
