export interface Schedule {
  id?: number,
  doctorId: number,
  dayStart: string,
  dayEnd: string,
  duration: string,
  hospitalId: number,
  dayOfWeek: string
}
