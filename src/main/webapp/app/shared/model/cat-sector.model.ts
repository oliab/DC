import { IUser } from 'app/core/user/user.model';

export interface ICatSector {
  id?: number;
  actividadEconomica?: string;
  actividadVulnerable?: boolean;
  usuario?: IUser;
}

export class CatSector implements ICatSector {
  constructor(public id?: number, public actividadEconomica?: string, public actividadVulnerable?: boolean, public usuario?: IUser) {
    this.actividadVulnerable = this.actividadVulnerable || false;
  }
}
