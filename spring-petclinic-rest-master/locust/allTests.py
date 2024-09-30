from locust import HttpUser, task, between

class PetClinicUser(HttpUser):
    wait_time = between(1, 3)
    base_path = "/petclinic/api"

    @task
    def get_list_owners(self):
        self.client.get(f"{self.base_path}/owners")

    @task
    def add_owner(self):
        owner_data = {
            "firstName": "John",
            "lastName": "Doe",
            "address": "123 Elm Street",
            "city": "Springfield",
            "telephone": "1234567890"
        }
        self.client.post(f"{self.base_path}/owners", json=owner_data)

    @task
    def update_owner(self):
        owner_id = 1  # Replace with valid ID
        updated_data = {
            "firstName": "Jane",
            "lastName": "Doe",
            "address": "456 Oak Street",
            "city": "Springfield",
            "telephone": "0987654321"
        }
        self.client.put(f"{self.base_path}/owners/{owner_id}", json=updated_data)

    @task
    def delete_owner(self):
        owner_id = 1  # Replace with valid ID
        self.client.delete(f"{self.base_path}/owners/{owner_id}")

    @task
    def get_list_pets(self):
        self.client.get(f"{self.base_path}/pets")

    @task
    def add_pet(self):
        owner_id = 1  # Replace with valid owner ID
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
    def update_pet(self):
        owner_id = 1  # Replace with valid owner ID
        pet_id = 1  # Replace with valid pet ID
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
    def delete_pet(self):
        owner_id = 1  # Replace with valid owner ID
        pet_id = 1  # Replace with valid pet ID
        self.client.delete(f"{self.base_path}/owners/{owner_id}/pets/{pet_id}")

    @task
    def add_visit(self):
        owner_id = 1  # Replace with valid owner ID
        pet_id = 1  # Replace with valid pet ID
        visit_data = {
            "date": "2023-09-20",
            "description": "General checkup"
        }
        self.client.post(f"{self.base_path}/owners/{owner_id}/pets/{pet_id}/visits", json=visit_data)
