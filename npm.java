import React, { useState, useEffect } from 'react';
import axios from 'axios';
import DishItem from './DishItem';

const DishList = () => {
  const [dishes, setDishes] = useState([]);

  useEffect(() => {
    fetchDishes();
  }, []);

  const fetchDishes = async () => {
    try {
      const response = await axios.get('http://localhost:3000/dishes');
      setDishes(response.data);
    } catch (error) {
      console.error('Error fetching dishes: ', error);
    }
  };

  const handleTogglePublish = async (dishId) => {
    try {
      await axios.put(`http://localhost:3000/dishes/${dishId}/togglePublish`);
      // Update the state to reflect the change
      setDishes(dishes.map(dish => {
        if (dish.dishId === dishId) {
          return { ...dish, isPublished: !dish.isPublished };
        }
        return dish;
      }));
    } catch (error) {
      console.error(`Error toggling publish status for dish ${dishId}: `, error);
    }
  };

  return (
    <div>
      <h2>Dishes</h2>
      {dishes.map(dish => (
        <DishItem key={dish.dishId} dish={dish} onTogglePublish={handleTogglePublish} />
      ))}
    </div>
  );
};

export default DishList;
import React from 'react';
import ToggleButton from './ToggleButton';

const DishItem = ({ dish, onTogglePublish }) => {
  const { dishId, dishName, imageUrl, isPublished } = dish;

  return (
    <div style={{ border: '1px solid #ccc', padding: '10px', margin: '10px 0' }}>
      <h3>{dishName}</h3>
      <img src={imageUrl} alt={dishName} style={{ maxWidth: '200px' }} />
      <p><strong>Published:</strong> {isPublished ? 'Yes' : 'No'}</p>
      <ToggleButton
        isPublished={isPublished}
        onClick={() => onTogglePublish(dishId)}
      />
    </div>
  );
};

export default DishItem;
import React from 'react';

const ToggleButton = ({ isPublished, onClick }) => {
  return (
    <button onClick={onClick}>
      {isPublished ? 'Unpublish' : 'Publish'}
    </button>
  );
};

export default ToggleButton;
import React from 'react';
import DishList from './DishList';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <h1>Dish Dashboard</h1>
      </header>
      <main>
        <DishList />
      </main>
    </div>
  );
}

export default App;
