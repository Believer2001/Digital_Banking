import {JwtPayload} from 'jwt-decode';

export  interface AuthMyJwtPayload extends JwtPayload{
  sub: string;
  scope: string[];
  exp?: number;
}
