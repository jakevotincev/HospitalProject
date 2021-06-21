import {Doctor} from "./doctor";
import {Patient} from "./patient";
import {Hospital} from "./hospital";

export interface Appointment {
  id?: number,
  doctor: Doctor,
  patient?: Patient,
  date: string,
  duration?: string,
  hospital: Hospital,
  comment: string
}
