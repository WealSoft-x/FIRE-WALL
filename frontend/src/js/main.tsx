import React from 'react'
import ReactDOM from 'react-dom'
import { Provider } from 'react-redux'
import configureStore from './stores/configure-store'
import createRoute from './routes/routes'
import rootReducer from './reducers/round-up-reducer'

const store = configureStore(rootReducer)
const routes = createRoute()

ReactDOM.render(<Provider store={store}>{routes}</Provider>, document.getElementById('app'))
