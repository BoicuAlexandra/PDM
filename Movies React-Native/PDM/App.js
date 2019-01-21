import React from 'react';
import { StyleSheet, Text, View } from 'react-native';
import  Lista  from './components/Lista';


export default class App extends React.Component {

  constructor(props) {
    super(props);
    this.state = { 
      movies: '121'
     };
  }
  
  componentDidMount() {
  }

  render() {
    return (
      <View style={styles.container}>
        <Lista />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
