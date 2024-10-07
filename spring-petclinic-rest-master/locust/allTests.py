import random, time 
from locust import HttpUser, task, between

def action_prob(prob):
    """
    @param prob: int - The minimum value (1-100) that the randomly generated number should meet or exceed.
    @return: bool - True if the random number (in the range of 1 to 100) is less than or equal to `prob`; otherwise, False.
    @raises ValueError: If `prob` is not within the valid range (1-100).
    Returns true if the random number is higher than the given parameter in a range of 1-100
    """
    if(random.randrange(1,100) <= prob):
        return False
    return True


class PetClinicUser(HttpUser):
    wait_time = between(1, 3)
    base_path = "/petclinic/api"

    @task
    def user_session_1(self):
        """Session 1: Add a new owner, then add a new pet to that owner"""
        # Step 1: Add a new owner
        owner_data = {
            "firstName": "John",
            "lastName": "Doe",
            "address": "123 Elm Street",
            "city": "Springfield",
            "telephone": "1234567890"
        }
        response = self.client.post(f"{self.base_path}/owners", json=owner_data)
        if response.status_code == 201:
            owner_id = response.json().get("id")

            # Step 2: Add a new pet to the newly created owner
            # NOTE: 30% probability 
            if(action_prob(30)): return 
            time.sleep(2) # Think Time of the user

            if owner_id:
                pet_data = {
                    "name": "Buddy",
                    "birthDate": "2015-05-15",
                    "type": {
                        "id": 1,
                        "name": "dog"
                    }
                }
                self.client.post(f"{self.base_path}/owners/{owner_id}/pets", json=pet_data)

    @task
    def user_session_2(self):
        """Session 2: Get the list of owners, update an owner, and then add a visit"""
        # Step 1: Get the list of owners
        response = self.client.get(f"{self.base_path}/owners")
        if response.status_code == 200 and response.json():
            owner = response.json()[0]
            owner_id = owner.get("id")

            # Step 2: Update the owner information
            if(action_prob(40)): return 
            time.sleep(2) # Think Time of the user

            updated_data = {
                "firstName": "Jane",
                "lastName": "Doe",
                "address": "456 Oak Street",
                "city": "Springfield",
                "telephone": "0987654321"
            }
            self.client.put(f"{self.base_path}/owners/{owner_id}", json=updated_data)

            # Step 3: Add a visit for the first pet of this owner
            if(action_prob(20)): return 
            time.sleep(2) # Think Time of the user

            if "pets" in owner and owner["pets"]:
                pet_id = owner["pets"][0]["id"]
                visit_data = {
                    "date": "2023-09-20",
                    "description": "General checkup"
                }
                self.client.post(f"{self.base_path}/owners/{owner_id}/pets/{pet_id}/visits", json=visit_data)

    @task
    def user_session_3(self):
        """Session 3: Get list of pets and update pet information"""
        # Step 1: Get the list of pets
        response = self.client.get(f"{self.base_path}/pets")
        if response.status_code == 200 and response.json():
            pet = response.json()[0]
            pet_id = pet.get("id")
            owner_id = pet.get("owner", {}).get("id")

            # Step 2: Update pet information
            if(action_prob(80)): return 
            time.sleep(2) # Think Time of the user

            if pet_id and owner_id:
                updated_pet_data = {
                    "name": "Buddy",
                    "birthDate": "2015-05-15",
                    "type": {
                        "id": 1,
                        "name": "dog"
                    }
                }
                self.client.put(f"{self.base_path}/owners/{owner_id}/pets/{pet_id}", json=updated_pet_data)


    @task
    def user_session_4(self):
        """Session 3: Get the list of pets and update a random pet"""
        # Step 1: Get the list of pets
        response = self.client.get(f"{self.base_path}/pets")
        if response.status_code == 200 and response.json():
            pets = response.json()
            pet = random.choice(pets)  # Scegli un pet casuale
            pet_id = pet.get("id")
            owner_id = pet.get("owner", {}).get("id")

            # Step 2: Update pet information
            if action_prob(80):
                return
            time.sleep(2)  # Think Time of the user

            if pet_id and owner_id:
                updated_pet_data = {
                    "name": "Buddy",
                    "birthDate": "2015-05-15",
                    "type": {
                        "id": pet.get("type", {}).get("id"),
                        "name": pet.get("type", {}).get("name")
                    }
                }
                self.client.put(f"{self.base_path}/owners/{owner_id}/pets/{pet_id}", json=updated_pet_data)


    @task
    def user_session_5(self):
        """Session 3: Get the list of pets and delete a random pet"""
        # Step 1: Get the list of pets
        response = self.client.get(f"{self.base_path}/pets")
        if response.status_code == 200 and response.json():
            pets = response.json()
            pet = random.choice(pets)  # Scegli un pet casuale
            pet_id = pet.get("id")

            # Step 2: Delete pet 
            time.sleep(2)  # Think Time of the user

            self.client.delete(f"{self.base_path}/pets/{pet_id}")

