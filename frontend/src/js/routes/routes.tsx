import React from 'react'
import { BrowserRouter, Route, Switch } from 'react-router-dom'
import MainContainer from '../containers/container'
import AuthentificationContainer from '../containers/authentification-container'
import TemporaryPasswordContainer from '../containers/temporarypassword-container'
import UpdatePasswordComponent from '../components/updatepassword-component'
import updatepasswordContainer from '../containers/updatepassword-container'

/**
 * Routing
 *
 * @return {*}
 */
export default () => {
  return (
    <BrowserRouter>
      <Switch>
        <Route exact path='/fire-wall' component={MainContainer} />
        <Route exact path='/fire-wall/authentification' component={AuthentificationContainer} />
        <Route exact path='/fire-wall/temporarypassword' component={TemporaryPasswordContainer} />
        <Route exact path='/fire-wall/updatepassword' component={updatepasswordContainer} />
      </Switch>
    </BrowserRouter>
  )
}
