📍 MicroService - Gestion des Destinations (DestinationService)
👩‍💻 Développé par : Chaieb Dhia
Ce microservice fait partie du projet EasyTrip, une plateforme de gestion de voyages. Il est responsable de la gestion des destinations touristiques proposées aux utilisateurs.

🚀 Fonctionnalités principales
➕ Ajouter une nouvelle destination avec détails (nom, description, localisation, etc.)
📄 Consulter les destinations (par ID, toutes, filtrées par critères)
🛠️ Mettre à jour ou supprimer une destination
🔍 Recherche avancée par nom, localisation, catégorie (plage, montagne, ville, etc.)
🌟 Classement des destinations par popularité (basé sur vues ou réservations)
✅ Validation des données (ex. unicité du nom, format des coordonnées)
🧱 Architecture
Base de données : H2
Communication inter-service : Feign Client (vérifie l'existence des utilisateurs ou voyages via user-service ou voyage-service)
API REST construite avec Spring Boot
Gestion des erreurs Feign intégrée (404 si utilisateur ou voyage non trouvé)
CORS activé pour autoriser l'accès depuis le front-end
Stockage des médias : Intégration avec un service de stockage (ex. AWS S3) pour images/vidéos
🔗 Endpoints REST
Méthode	URL	Description
POST	/api/destinations	Créer une nouvelle destination
GET	/api/destinations/{id}	Détails d’une destination
GET	/api/destinations	Liste de toutes les destinations
PUT	/api/destinations/{id}	Modifier une destination
DELETE	/api/destinations/{id}	Supprimer une destination
GET	/api/destinations/recherche	Recherche avancée par nom, localisation, catégorie
GET	/api/destinations/populaires	Liste triée par popularité (vues/réservations)
POST	/api/destinations/{id}/media	Ajouter une image/vidéo à une destination
GET	/api/destinations/{id}/media	Récupérer les médias d’une destination
🛠️ Recommended Option: Integration with Map Services
For an enhanced user experience, we recommend integrating this microservice with a mapping API (e.g., Google Maps or OpenStreetMap) to provide interactive maps for destinations. This can be achieved by:

Storing latitude/longitude in the database for each destination.
Exposing an endpoint like /api/destinations/{id}/map to return GeoJSON data for frontend rendering.
Using a third-party library like Leaflet or Mapbox for frontend map visualization.
This integration allows users to explore destinations visually, improving engagement and usability.

📝 Notes
Ensure proper validation of coordinates (latitude/longitude) when adding or updating destinations.
Use environment variables for sensitive configurations (e.g., API keys for media storage or map services).
For scalability, consider caching popular destinations using Redis or a similar solution.
