import {AuthConfig} from 'angular-oauth2-oidc';

export const authConfig: AuthConfig = {
  issuer: 'https://login.microsoftonline.com/8df7cb8e-002d-40f3-808f-9ac09d86b7f9/v2.0',
  redirectUri: window.location.origin + '/doctors',
  // postLogoutRedirectUri: window.location.origin + '/patients',
  clientId: '21b848a3-edee-4056-a554-54e673c80815',
  responseType: 'code',
  strictDiscoveryDocumentValidation: false,
  scope: 'openid api://21b848a3-edee-4056-a554-54e673c80815/app',
}
