export interface LoginResponse {
  token: string;
}

export interface User {
  id: number;
  username: string;
  email: string;
  password: string;
  firstname: string;
  lastname: string;
  roles: string[];
}

export interface Page<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  numberOfElements: number;
  first: boolean;
  last: boolean;
  empty: boolean;
}
