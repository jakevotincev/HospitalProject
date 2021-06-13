import {Hospital} from "./hospital";

export interface Doctor {
  id?: number,
  surname: string,
  name: string,
  middleName: string,
  speciality: string,
  hospitals: Hospital[]
}
