import { bindActionCreators } from 'redux'
import { connect } from 'react-redux'
import MainComponent from '../components/main-component'
import * as SampleActions from '../actions/actions'
import rootReducer from '../reducers/round-up-reducer'
import AuthentificationComponent from '../components/authentification-component'

/**
 * import-search-container Container
 *
 */
export default connect(
  (state) => {
    return {
      state: {
        SampleReducer: rootReducer.SampleReducer,
      },
    }
  },
  (dispatch) => {
    return {
      actions: {
        SampleActions: bindActionCreators(SampleActions, dispatch),
      },
    }
  }
)(AuthentificationComponent)
