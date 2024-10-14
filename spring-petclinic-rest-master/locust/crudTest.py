import random
from locust import HttpUser, task, between

class PetClinicUser(HttpUser):
    wait_time = between(1, 3)
    base_path = "/petclinic/api"

    # PetTypes CRUD tasks
    @task
    def get_all_pettypes(self):
        response = self.client.get(f"{self.base_path}/pettypes")
        if response.status_code == 200:
            print("GET all pettypes success")
        else:
            print(f"GET all pettypes failed: {response.status_code}")

    @task
    def update_pettype(self):
        response = self.client.get(f"{self.base_path}/pettypes")
        if response.status_code == 200 and response.json():
            pettype = random.choice(response.json())
            pettype_id = pettype["id"]
            updated_pettypes_data = {
                "name": f"{pettype['name']}_updated"
            }
            self.client.put(f"{self.base_path}/pettypes/{pettype_id}", json=updated_pettypes_data)
        else:
            print(f"PUT pettype failed: {response.status_code}")

    # Owners CRUD tasks
    @task
    def update_owner(self):
        response = self.client.get(f"{self.base_path}/owners")
        if response.status_code == 200 and response.json():
            owner = random.choice(response.json())
            owner_id = owner["id"]
            updated_owner_data = {
                "firstName": f"{owner['firstName']}_updated",
                "lastName": owner["lastName"],
                "address": owner["address"],
                "city": owner["city"],
                "telephone": owner["telephone"]
            }
            self.client.put(f"{self.base_path}/owners/{owner_id}", json=updated_owner_data)
        else:
            print(f"PUT owner failed: {response.status_code}")

    # Pets CRUD tasks
    @task
    def update_pet(self):
        response = self.client.get(f"{self.base_path}/pets")
        if response.status_code == 200 and response.json():
            pet = random.choice(response.json())
            pet_id = pet["id"]
            updated_pet_data = {
                "name": f"{pet['name']}_updated",
                "birthDate": pet["birthDate"],  # Deve essere nel formato YYYY-MM-DD
                "type": pet["type"]  # Deve includere l'oggetto `type` con id e nome
            }
            self.client.put(f"{self.base_path}/pets/{pet_id}", json=updated_pet_data)
        else:
            print(f"PUT pet failed: {response.status_code}")

    # Vets CRUD tasks
    @task
    def update_vet(self):
        response = self.client.get(f"{self.base_path}/vets")
        if response.status_code == 200 and response.json():
            vet = random.choice(response.json())
            vet_id = vet["id"]
            updated_vet_data = {
                "firstName": f"{vet['firstName']}_updated",
                "lastName": vet["lastName"],
                "specialties": vet["specialties"]  # Deve essere un array di oggetti specialty con id e nome
            }
            self.client.put(f"{self.base_path}/vets/{vet_id}", json=updated_vet_data)
        else:
            print(f"PUT vet failed: {response.status_code}")

    # Specialties CRUD tasks
    @task
    def update_specialty(self):
        response = self.client.get(f"{self.base_path}/specialties")
        if response.status_code == 200 and response.json():
            specialty = random.choice(response.json())
            specialty_id = specialty["id"]
            updated_specialty_data = {
                "name": f"{specialty['name']}_updated"
            }
            self.client.put(f"{self.base_path}/specialties/{specialty_id}", json=updated_specialty_data)
        else:
            print(f"PUT specialty failed: {response.status_code}")
