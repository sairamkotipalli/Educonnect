<<<<<<< HEAD
export class Course {
  courseId: number;
  courseName: string;
  description: string;
  teacherId: number;
  constructor(courseId: number, courseName: string, description: string, teacherId: number) {
    this.courseId = courseId;
    this.courseName = courseName;
    this.description = description;
    this.teacherId = teacherId;
  }
  logAttributes(): void {
    console.log('courseId:', this.courseId);
    console.log('courseName:', this.courseName);
    console.log('description:', this.description);
    console.log('teacherId:', this.teacherId);
  }
=======

export class Course {

>>>>>>> d76e0db293a8626f50b912ae8482fe15a23e1abc
}