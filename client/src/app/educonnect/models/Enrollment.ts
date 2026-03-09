<<<<<<< HEAD
export class Enrollment {
  enrollmentId: number;
  studentId: number;
  courseId: number;
  enrollmentDate: Date;
  constructor(enrollmentId: number, studentId: number, courseId: number, enrollmentDate: Date) {
    this.enrollmentId = enrollmentId;
    this.studentId = studentId;
    this.courseId = courseId;
    this.enrollmentDate = enrollmentDate;
  }
  logAttributes(): void {
    console.log('enrollmentId:', this.enrollmentId);
    console.log('studentId:', this.studentId);
    console.log('courseId:', this.courseId);
    console.log('enrollmentDate:', this.enrollmentDate);
  }
}
=======

export class Enrollment {

}

>>>>>>> d76e0db293a8626f50b912ae8482fe15a23e1abc
